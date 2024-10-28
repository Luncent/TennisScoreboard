package org.example.Validators;

import org.example.DTO.MatchCreateDTO;
import org.example.Exceptions.ValidationException;

public class MatchValidator {
    public boolean validateCreateDTO(MatchCreateDTO dto) throws ValidationException {
        String player1Name = dto.getPlayer1Name();
        String player2Name = dto.getPlayer2Name();

        if (player1Name.trim().isEmpty() || player2Name.trim().isEmpty()) {
            throw new ValidationException("Имя игрока не должно быть пустым");
        }
        if (!onlyLetters(player1Name) || !onlyLetters(player1Name)) {
            throw new ValidationException("Имя игрока содержит недопустимые символы");
        }
        if(player1Name.equals(player2Name)){
            throw new ValidationException("Игрок не может играть сам с собой");
        }
        return true;
    }

    private boolean onlyLetters(String str) {
        return str.matches("^[a-zA-Zа-яА-ЯёЁ0-9]+$");
    }
}
