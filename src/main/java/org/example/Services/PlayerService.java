package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.example.Entities.Player;
import org.example.Exceptions.EmptyException;
import org.example.Repositories.PlayerRepository;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PlayerService {
    private final SessionSupplier supplier;

    public List<Player> getAll() {
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try {
                PlayerRepository repo = new PlayerRepository(session);
                List<Player> players = repo.getAll();
                t.commit();
                return players;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public Optional<Player> getByName(String name) {
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try {
                PlayerRepository repo = new PlayerRepository(session);
                Optional<Player> player = repo.getByName(name);
                t.commit();
                return player;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public Optional<Player> getById(Long id){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try {
                PlayerRepository repo = new PlayerRepository(session);
                Optional<Player> player = repo.getById(id);
                t.commit();
                return player;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public Player save(org.example.DTO.PlayerCreateDTO dto){
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try {
                Player player = Player.builder().name(dto.getName()).build();
                PlayerRepository repo = new PlayerRepository(session);
                Player savedPlayer = repo.save(player);
                t.commit();
                return savedPlayer;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}
