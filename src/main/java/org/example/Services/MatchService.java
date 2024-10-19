package org.example.Services;

import lombok.AllArgsConstructor;
import org.example.DTO.MatchCreateDTO;
import org.example.Entities.Match;
import org.example.Repositories.MatchRepository;
import org.example.Repositories.PlayerRepository;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    Match save(Match entity){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                MatchRepository repo = new MatchRepository(session);
                Match match = repo.save(entity);
                t.commit();
                return match;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}
