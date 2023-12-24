package org.airline.Dao;

import java.util.List;

import org.airline.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query("from Ticket as t where t.user.uId=:id")
	public List<Ticket> findBookTicketsByUser(@Param("id") Long id);
}
