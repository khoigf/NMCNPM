package org.airline.Service;

import java.util.List;

import org.airline.Dao.TicketRepository;
import org.airline.Entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository book_TicketRepository;

	@Override
	public List<Ticket> findBookTicketsByUser(Long getuId) {
		System.out.println(getuId);
		return this.book_TicketRepository.findBookTicketsByUser(getuId);
	}

	@Override
	public Ticket findById(Long tId) {

		return this.book_TicketRepository.findById(tId).isPresent() ? this.book_TicketRepository.findById(tId).get()
				: null;
	}

	@Override
	public void delete(Ticket book_Ticket) {
		this.book_TicketRepository.delete(book_Ticket);

	}

	@Override
	public List<Ticket> findAllTicket() {

		return this.book_TicketRepository.findAll();
	}

}
