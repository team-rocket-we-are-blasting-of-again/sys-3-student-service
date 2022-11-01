package com.teamrocket.sys3studentservice.application.grcp;

import com.teamrocket.BookPurchaseServiceGrpc.BookPurchaseServiceImplBase;
import com.teamrocket.BookToBuy;
import com.teamrocket.BoughtBook;
import com.teamrocket.BoughtBookReply;
import com.teamrocket.Recommendation;
import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.service.CourseService;
import com.teamrocket.sys3studentservice.service.StudentService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookPurchaseServiceImpl extends BookPurchaseServiceImplBase {

    private final StudentService studentService;
    private final CourseService courseService;
    private final KafkaTemplate<String, IDDto<Long>> kafkaTemplate;

    @Override
    public void purchaseBook(BookToBuy request, StreamObserver<BoughtBookReply> responseObserver) {
        BookDto bookDto = BookDto.builder()
            .id(request.getBookId())
            .price(request.getPrice())
            .build();
        IDDto<Long> bookBought = studentService.addBookToStudent(request.getStudentId(), bookDto);
        List<IDDto<Long>> recommendations = courseService.getRecommendations(bookBought.getId());
        List<Recommendation> recommendationsReply = new ArrayList<>();
        for (IDDto dto: recommendations
             ) {
            recommendationsReply.add(Recommendation.newBuilder().setId((Long) dto.getId()).build());
        }
        kafkaTemplate.send("bookBought", bookBought);
        BoughtBookReply reply = BoughtBookReply.newBuilder()
                .setBoughtBookId(bookBought.getId())
                .addAllRecommendations(recommendationsReply)
                .build();
        responseObserver.onNext(reply);
    }
}
