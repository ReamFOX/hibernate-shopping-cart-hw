package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.dao.TicketDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao shoppingCartDao;
    @Inject
    TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovieSession(movieSession);
        ticketDao.add(ticket);
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user).get();
        shoppingCart.getTickets().add(ticket);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user).orElseThrow(() ->
                new RuntimeException("Shopping cart for user " + user + " doesn`t exist"));
    }

    @Override
    public void registerNewShoppingCart(User user) {
        if (shoppingCartDao.getByUser(user).isEmpty()) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCartDao.add(shoppingCart);
        }
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {

    }
}
