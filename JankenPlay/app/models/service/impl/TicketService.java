package models.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import models.entity.Ticket;
import models.service.ModelService;
import play.Logger;

public class TicketService implements ModelService<Ticket> {

    @Override
    public Optional<Ticket> findById(Long id) {
        Ticket selectResult = Ticket.find.byId(id);
        Optional<Ticket> result = Optional.ofNullable(selectResult);

        return result;
    }

    @Override
    public boolean store(Ticket model) {
        try {
            model.save();

            return true;
        } catch (PersistenceException e) {
            Logger.error("TicketServiceEBean store failed", e);

            return false;
        }
    }

    @Override
    public List<Ticket> getAll() {
        return Ticket.find.all();
    }
}
