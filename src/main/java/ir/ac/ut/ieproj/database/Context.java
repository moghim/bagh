package ir.ac.ut.ieproj.database;

import org.hibernate.Session;

public class Context {
	private static ThreadLocal<Session> session = new ThreadLocal<Session>();

	public static Session getSession() {
		if (session.get() == null)
			session.set(HibernateUtil.getSessionFactory().openSession());
		return session.get();
	}

	public static void closeSession() {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().close();
		} finally {
			session.set(null);
		}
	}
}