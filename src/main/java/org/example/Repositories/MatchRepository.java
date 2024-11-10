package org.example.Repositories;

import org.example.Entities.Match;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MatchRepository extends BaseRepository<Match,Long>{

    public MatchRepository(Session sessionProxy) {
        super(sessionProxy, Match.class);
        graph = sessionProxy.createEntityGraph(Match.class);
        graph.addAttributeNodes("player1","player2","winner");
        properties = new HashMap<>();
        properties.put(GraphSemantic.LOAD.getJpaHintName(), graph);
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
        if(name==null || name.isEmpty()){
            return sessionProxy.createQuery("FROM Match m",clazz)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), graph) // not sure what this thing does, may be can be removed? or put in main config, as is repeated?
                    .list();
        }
        return sessionProxy
                .createQuery("FROM Match m WHERE LOWER(m.player1.name) LIKE LOWER(:name)" +
                        " OR LOWER(m.player2.name) LIKE LOWER(:name)",clazz)
                .setParameter("name","%"+name+"%")
                .setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                .list();
    }

    @Override
    public Match save(Match entity) {
        return super.save(entity);
    }

    public Long getRowsCount(String playerName) {
        if(playerName==null || playerName.isEmpty()){
            return sessionProxy.createQuery("SELECT COUNT(*) FROM Match m",Long.class).uniqueResult();
        }
        else{
            return sessionProxy
                    .createQuery("SELECT COUNT(*) FROM Match m " +
                            "WHERE LOWER(m.player1.name) LIKE LOWER(:name)" +
                            " OR LOWER(m.player2.name) LIKE LOWER(:name)", Long.class)
                    .setParameter("name","%"+playerName+"%")
                    .uniqueResult();
        }
    }

    public List<Match> selectPaginated(String playerName, int page, int pageSize) {
        if(playerName==null || playerName.isEmpty()){ // you could prevent writing this extra code by early param validation
            return sessionProxy.createQuery("FROM Match m",clazz)
                    .setFirstResult((page-1)*pageSize)
                    .setMaxResults(pageSize)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                    .list();
        }
        else{
            return sessionProxy.createQuery("FROM Match m WHERE" +
                            " LOWER(m.player1.name) LIKE LOWER(:name)" +
                            " OR LOWER(m.player2.name) LIKE LOWER(:name)",clazz) // query is duplicated; can be a const
                    .setParameter("name", "%"+playerName+"%")
                    .setFirstResult((page-1)*pageSize)
                    .setMaxResults(pageSize)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                    .list();
        }
    }
}
