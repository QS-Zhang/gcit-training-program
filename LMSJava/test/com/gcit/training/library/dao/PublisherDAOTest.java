package com.gcit.training.library.dao;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;

import com.gcit.training.library.domain.Publisher;

public class PublisherDAOTest {
	@Test
	public void testCreate() {
		Publisher pub = new Publisher();
		pub.setPublisherName("Some new publisherName");
		pub.setPublisherAddress("Some new publisherAddres");
		pub.setPublisherPhone("Some new publisherPhone");
		try {
			new PublisherDAO().create(pub);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Publisher create failed!"); 
		}
	}

	@Test
	public void testUpdate() {
		Publisher pub = new Publisher();
		pub.setPublisherId(1);
		pub.setPublisherName("Some other name");
		pub.setPublisherAddress("some other address");
		pub.setPublisherPhone("some other phone");
		try {
			new PublisherDAO().update(pub);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Publisher update failed!"); 
		}
	}

	@Test
	public void testDelete() {
		Publisher pub = new Publisher();
		pub.setPublisherId(1);
		try {
			new PublisherDAO().delete(pub);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Publisherr delete failed!"); 
		}
	}

}
