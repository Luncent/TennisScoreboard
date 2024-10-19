package org.example.Repositories;

import org.example.Entities.Player;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PlayerRepository extends BaseRepository<Player, Long>{

    public PlayerRepository(Session sessionProxy){
        super(sessionProxy, Player.class);
    }

    @Override
    public Player save(Player entity) {
        return super.save(entity);
    }

    @Override
    public List<Player> getAll() {
        return super.getAll();
    }

    @Override
    public Optional<Player> getById(Long id) {
        return super.getById(id);
    }

    public Optional<Player> getByName(String name) {
        return Optional.ofNullable(sessionProxy.
                createQuery("FROM Player p WHERE p.name = :name",clazz)
                .setParameter("name",name)
                .uniqueResult());
    }

    @Override
    public Player update(Player entity) {
        return super.update(entity);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }
}
