package normalTests;

import java.util.Iterator;
import java.util.List;

import model.Task;
import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.HibernateUtil;

public class HibernateTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		HibernateTests tst = new HibernateTests();

		/**
		 * adding records
		 */
		tst.addUser("Saranga", "Rath");
		tst.addUser("Isuru", "Sampath");
		tst.addUser("Saranga", "Jaya");
		tst.addUser("Prasanna", "Milinda");

		tst.addTask(1, "Call", "Call Pubudu at 5 PM");
		tst.addTask(1, "Shopping", "Buy some foods for Kity");
		tst.addTask(2, "Email", "Send birthday wish to Pubudu");
		tst.addTask(2, "SMS", "Send message to Dad");
		tst.addTask(2, "Office", "Give a call to Boss");

		/**
		 * retrieving data
		 */
		tst.getFullName("Saranga");

		/**
		 * full updating records
		 */
		User user = new User();
		user.setId(1);
		user.setFirstName("Saranga");
		user.setLastName("Rathnayake");
		tst.updateUser(user);

		/**
		 * partial updating records
		 */
		tst.updateLastName(3, "Jayamaha");

		/**
		 * deleting records
		 */
		User user1 = new User();
		user1.setId(4);
		tst.deleteUser(user1);
	}

	private void addUser(String firstName, String lastName) {

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			User user = new User();

			user.setFirstName(firstName);
			user.setLastName(lastName);

			session.save(user);

			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	private void addTask(int userID, String title, String description) {

		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			Task task = new Task();

			task.setUserID(userID);
			task.setTitle(title);
			task.setDescription(description);

			session.save(task);

			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	private void updateLastName(int id, String lastName) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			String hqlUpdate = "update User u set u.lastName = :newLastName where u.id = :oldId";
			int updatedEntities = session.createQuery(hqlUpdate)
					.setString("newLastName", lastName).setInteger("oldId", id)
					.executeUpdate();

			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

	}

	private void updateUser(User user) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.update(user);

			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	private void getFullName(String firstName) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			List<User> users = session
					.createQuery(
							"from User as u where u.firstName = :firstName")
					.setString("firstName", firstName).list();
			for (Iterator<User> iter = users.iterator(); iter.hasNext();) {
				User user = iter.next();
				System.out.println(user.getFirstName() + " "
						+ user.getLastName());
			}
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	private void deleteUser(User user) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();

			session.delete(user);

			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
}
