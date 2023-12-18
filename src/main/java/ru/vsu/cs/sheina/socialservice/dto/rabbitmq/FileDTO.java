package ru.vsu.cs.sheina.socialservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class FileDTO {

    String url;

    MultipartFile file;
}
