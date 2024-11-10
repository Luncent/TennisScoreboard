package org.example.Services;

import lombok.AllArgsConstructor;
import org.example.Entities.Match;
import org.example.Repositories.MatchRepository;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MatchService {
    private SessionSupplier supplier;

    public List<Match> getAll(){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                MatchRepository repo = new MatchRepository(session); 
                // same comment as in prev project, don't think you need to create a new repo object all the time.
                List<Match> matches = repo.getAll();
                t.commit();
                return matches;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public List<Match> selectPaginated(String playerName, int page, int pageSize) {
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                MatchRepository repo = new MatchRepository(session);
                List<Match> paginatedMatches = repo.selectPaginated(playerName, page, pageSize);
                t.commit();
                return paginatedMatches;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public List<Match> getByPlayerName(String name){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                MatchRepository repo = new MatchRepository(session);
                List<Match> matches = repo.getByPlayerName(name);
                t.commit();
                return matches;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public Optional<Match> getById(Long id){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                MatchRepository repo = new MatchRepository(session);
                Optional<Match> match = repo.getById(id);
                t.commit();
                return match;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public Long getRowsCount(String playerName){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                MatchRepository repo = new MatchRepository(session);
                Long rows = repo.getRowsCount(playerName);
                t.commit();
                return rows;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}
