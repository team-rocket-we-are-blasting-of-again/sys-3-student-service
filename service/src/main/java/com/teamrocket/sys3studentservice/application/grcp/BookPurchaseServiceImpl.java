package com.teamrocket.sys3studentservice.application.grcp;

import com.google.protobuf.BoolValue;
import com.teamrocket.BookPurchaseServiceGrpc.BookPurchaseServiceImplBase;
import com.teamrocket.BookToBuy;
import com.teamrocket.BoughtBookReply;
import com.teamrocket.Recommendation;
import com.teamrocket.StudentInfo;
import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.service.CourseService;
import com.teamrocket.sys3studentservice.service.StudentService;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@GrpcService
public class BookPurchaseServiceImpl extends BookPurchaseServiceImplBase {

    private final StudentService studentService;
    private final CourseService courseService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void purchaseBook(BookToBuy request, StreamObserver<BoughtBookReply> responseObserver) {
        BookDto bookDto =
                BookDto.builder().id(request.getBookId()).price(request.getPrice()).build();
        IDDto<Long> bookBought = studentService.addBookToStudent(request.getStudentId(), bookDto);
        List<IDDto<Long>> recommendations = courseService.getRecommendations(bookBought.getId());
        List<Recommendation> recommendationsReply = new ArrayList<>();
        for (IDDto<Long> dto : recommendations) {
            recommendationsReply.add(Recommendation.newBuilder().setId((Long) dto.getId()).build());
        }
        kafkaTemplate.send("bookBought", String.valueOf(bookDto.getId()));
        BoughtBookReply reply = BoughtBookReply.newBuilder().setBoughtBookId(bookBought.getId())
                .addAllRecommendations(recommendationsReply).build();
        responseObserver.onNext(reply);
    }

    @Override
    public void studentHasFunds(StudentInfo request, StreamObserver<BoolValue> responseObserver) {
        try {
            boolean hasFunds =
                    studentService.studentHasFunds(request.getStudentId(), request.getPrice());
            responseObserver.onNext(BoolValue.of(hasFunds));
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
