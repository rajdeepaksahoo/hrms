package org.vrnda.hrms.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AOPConfiguration {
	private Logger logger = LogManager.getLogger(AOPConfiguration.class.getName());

	@Before(value = "within(net.syscon.s4.*))")
	public void beforeExecution(final JoinPoint joinpoint) {
		logger.debug("Method in : " + joinpoint.getSignature());
	}

	@After(value = "within(net.syscon.s4.*)")
	public void afterExecution(final JoinPoint joinpoint) {
		logger.debug("Method out : " + joinpoint.getSignature());
	}

	@AfterThrowing(value = "within(net.syscon.s4.*)", throwing = "ex")
	public void throwException(final JoinPoint joinpoint, Exception ex) {
		logger.error("Exception occured in " + joinpoint.getSignature() + " : ", ex);
	}

	@Before("execution(* org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate.*(String, ..))")
	public void log(final JoinPoint jp) throws Throwable {
		Object[] methodArgs = jp.getArgs(), sqlArgs = null;

		String statement = methodArgs[0].toString();
          int n = methodArgs.length;
		for (int i = 1; i < methodArgs.length; i++) {
			Object arg = methodArgs[i];
			if (arg instanceof Object[]) {
				sqlArgs = (Object[]) arg;
				break;
			}
		}

		// fill in any SQL parameter place-holders (?'s)
		String completedStatement = (sqlArgs == null ? statement : fillParameters(statement, sqlArgs));

		// log it
		logger.debug(completedStatement);
	}

	private String fillParameters(final String statement, final Object[] sqlArgs) {
		// initialize a StringBuilder with a guesstimated final length
		StringBuilder completedSqlBuilder = new StringBuilder(Math.round(statement.length() * 1.2f));
		int index, // will hold the index of the next ?
				prevIndex = 0; // will hold the index of the previous ? + 1

		// loop through each SQL argument
		for (Object arg : sqlArgs) {
			index = statement.indexOf("?", prevIndex);
			if (index == -1)
				break; // bail out if there's a mismatch in # of args vs. ?'s

			// append the chunk of SQL coming before this ?
			completedSqlBuilder.append(statement.substring(prevIndex, index));
			// append the replacement for the ?
			if (arg == null)
				completedSqlBuilder.append("NULL");
			else
				completedSqlBuilder.append(arg.toString());

			prevIndex = index + 1;
		}

		// add the rest of the SQL if any
		if (prevIndex != statement.length())
			completedSqlBuilder.append(statement.substring(prevIndex));
		return completedSqlBuilder.toString();
	}

}

