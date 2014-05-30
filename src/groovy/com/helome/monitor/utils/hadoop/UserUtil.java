package com.helome.monitor.utils.hadoop;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Methods;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * Bridges {@code User} invocations to underlying calls to
 * {@link org.apache.hadoop.security.UserGroupInformation} for secure Hadoop
 * 0.20 and versions 0.21 and above.
 */
public class UserUtil extends User {
	private String shortName;

	private UserUtil() throws IOException {
		try {
			ugi = (UserGroupInformation) callStatic("getCurrentUser");
		} catch (IOException ioe) {
			throw ioe;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected exception getting current secure user");
		}
	}

	private UserUtil(UserGroupInformation ugi) {
		this.ugi = ugi;
	}

	@Override
	public String getShortName() {
		if (shortName != null)
			return shortName;

		try {
			shortName = (String) call(ugi, "getShortUserName", null, null);
			return shortName;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected error getting user short name");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T runAs(PrivilegedAction<T> action) {
		try {
			return (T) call(ugi, "doAs",
					new Class[] { PrivilegedAction.class },
					new Object[] { action });
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected exception in runAs()");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T runAs(PrivilegedExceptionAction<T> action)
			throws IOException, InterruptedException {
		try {
			return (T) call(ugi, "doAs",
					new Class[] { PrivilegedExceptionAction.class },
					new Object[] { action });
		} catch (IOException ioe) {
			throw ioe;
		} catch (InterruptedException ie) {
			throw ie;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected exception in runAs(PrivilegedExceptionAction)");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void obtainAuthTokenForJob(Configuration conf, Job job)
			throws IOException, InterruptedException {
		try {
			Class c = Class
					.forName("org.apache.hadoop.hbase.security.token.TokenUtil");
			Methods.call(c, null, "obtainTokenForJob", new Class[] {
					Configuration.class, UserGroupInformation.class,
					Job.class }, new Object[] { conf, ugi, job });
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Failure loading TokenUtil class, "
					+ "is secure RPC available?", cnfe);
		} catch (IOException ioe) {
			throw ioe;
		} catch (InterruptedException ie) {
			throw ie;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected error calling TokenUtil.obtainAndCacheToken()");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void obtainAuthTokenForJob(JobConf job) throws IOException,
			InterruptedException {
		try {
			Class c = Class
					.forName("org.apache.hadoop.hbase.security.token.TokenUtil");
			Methods.call(c, null, "obtainTokenForJob", new Class[] {
					JobConf.class, UserGroupInformation.class },
					new Object[] { job, ugi });
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Failure loading TokenUtil class, "
					+ "is secure RPC available?", cnfe);
		} catch (IOException ioe) {
			throw ioe;
		} catch (InterruptedException ie) {
			throw ie;
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected error calling TokenUtil.obtainAndCacheToken()");
		}
	}

	/**
	 * @see User#createUserForTesting(org.apache.hadoop.conf.Configuration,
	 *      String, String[])
	 */
	public static User createUserForTesting(Configuration conf,
			String name, String[] groups) {
		try {
			return new UserUtil(
					(UserGroupInformation) callStatic(
							"createUserForTesting", new Class[] {
									String.class, String[].class },
							new Object[] { name, groups }));
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Error creating secure test user");
		}
	}

	/**
	 * Obtain credentials for the current process using the configured
	 * Kerberos keytab file and principal.
	 * 
	 * @see User#login(org.apache.hadoop.conf.Configuration, String, String,
	 *      String)
	 * 
	 * @param conf
	 *            the Configuration to use
	 * @param fileConfKey
	 *            Configuration property key used to store the path to the
	 *            keytab file
	 * @param principalConfKey
	 *            Configuration property key used to store the principal
	 *            name to login as
	 * @param localhost
	 *            the local hostname
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void login(Configuration conf, String fileConfKey,
			String principalConfKey, String localhost) throws IOException {
		if (isSecurityEnabled()) {
			// check for SecurityUtil class
			try {
				Class c = Class
						.forName("org.apache.hadoop.security.SecurityUtil");
				Class[] types = new Class[] { Configuration.class,
						String.class, String.class, String.class };
				Object[] args = new Object[] { conf, fileConfKey,
						principalConfKey, localhost };
				Methods.call(c, null, "login", types, args);
			} catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(
						"Unable to login using "
								+ "org.apache.hadoop.security.SecurityUtil.login(). SecurityUtil class "
								+ "was not found!  Is this a version of secure Hadoop?",
						cnfe);
			} catch (IOException ioe) {
				throw ioe;
			} catch (RuntimeException re) {
				throw re;
			} catch (Exception e) {
				throw new UndeclaredThrowableException(e,
						"Unhandled exception in User.login()");
			}
		}
	}

	/**
	 * Returns the result of
	 * {@code UserGroupInformation.isSecurityEnabled()}.
	 */
	public static boolean isSecurityEnabled() {
		try {
			return (Boolean) callStatic("isSecurityEnabled");
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e,
					"Unexpected exception calling UserGroupInformation.isSecurityEnabled()");
		}
	}
	
	/* Reflection helper methods */
	private static Object callStatic(String methodName) throws Exception {
		return call(null, methodName, null, null);
	}

	@SuppressWarnings("rawtypes")
	private static Object callStatic(String methodName, Class[] types,
			Object[] args) throws Exception {
		return call(null, methodName, types, args);
	}

	@SuppressWarnings("rawtypes")
	private static Object call(UserGroupInformation instance,
			String methodName, Class[] types, Object[] args) throws Exception {
		return Methods.call(UserGroupInformation.class, instance, methodName,
				types, args);
	}
}
