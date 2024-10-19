package org.example.Repositories;

import org.example.Entities.Match;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class MatchRepository extends BaseRepository<Match,Long>{

    public MatchRepository(Session sessionProxy) {
        super(sessionProxy, Match.class);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public Match update(Match entity) {
        return super.update(entity);
    }

    @Override
    public Optional<Match> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<Match> getAll() {
        return super.getAll();
    }

    public List<Match> getByPlayerName(String name){
        return sessionProxy
                .createQuery("FROM Match m WHERE m.player1.name=:name" +
                        " OR m.player2.name=:name OR m.winner.name=:name",clazz)
                .setParameter("name",name).list();
    }

    @Override
    public Match save(Match entity) {
        return super.save(entity);
    }
}
