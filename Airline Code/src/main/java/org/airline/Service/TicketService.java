package org.airline.Service;

import java.util.List;

import org.airline.Entity.Ticket;

public interface TicketService {

	List<Ticket> findBookTicketsByUser(Long getuId);

	Ticket findById(Long tId);

	void delete(Ticket book_Ticket);

	List<Ticket> findAllTicket();

}
