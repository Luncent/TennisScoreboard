package org.example.Validators;

import org.example.DTO.PlayerCreateDTO;
import org.example.Exceptions.ValidationException;

public class PlayerValidator {
    public boolean validate(PlayerCreateDTO dto) throws ValidationException {
        String name = dto.getName();
        if (name.trim().isEmpty()) {
            throw new ValidationException("Имя игрока не должно быть пустым");
        }
        if (!onlyLetters(name)) {
            throw new ValidationException("Имя игрока содержит недопустимые символы");
        }
        return true;
    }

    private boolean onlyLetters(String str) {
        return str.matches("^[a-zA-Zа-яА-ЯёЁ]+$");
    }
}
