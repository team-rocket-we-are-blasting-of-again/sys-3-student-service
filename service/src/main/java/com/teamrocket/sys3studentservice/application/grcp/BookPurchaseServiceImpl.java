package com.teamrocket.sys3studentservice.application.grcp;

import com.teamrocket.BookPurchaseServiceGrpc.BookPurchaseServiceImplBase;
import com.teamrocket.BookToBuy;
import com.teamrocket.BoughtBook;
import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.service.StudentService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookPurchaseServiceImpl extends BookPurchaseServiceImplBase {

    private final StudentService studentService;
    private final KafkaTemplate<String, IDDto<Long>> kafkaTemplate;

    @Override
    public void purchaseBook(BookToBuy request, StreamObserver<BoughtBook> responseObserver) {
        BookDto bookDto = BookDto.builder()
            .id(request.getBookId())
            .price(request.getPrice())
            .build();
        IDDto<Long> bookBought = studentService.addBookToStudent(request.getStudentId(), bookDto);
        kafkaTemplate.send("bookBought", bookBought);
        responseObserver.onNext(BoughtBook.newBuilder().setId(bookBought.getId()).build());
    }
}
