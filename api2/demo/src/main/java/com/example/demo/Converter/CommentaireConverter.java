package com.example.demo.Converter;

import com.example.demo.Entities.CommentaireEntity.CommentaireDto;
import com.example.demo.Entities.CommentaireEntity.CommentaireEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentaireConverter {

    public CommentaireDto entityToDTO(CommentaireEntity commentaireEntity) {
        return new CommentaireDto() {{
            setId(commentaireEntity.getId());
            setText(commentaireEntity.getText());
            setSendDate(commentaireEntity.getSendDate());
            setWay(commentaireEntity.isWay());
            setSeen(commentaireEntity.isSeen());
            // Ajoutez d'autres propriétés de l'entité CommentaireEntity ici
        }};
    }
}
