package com.bsu.server.theoretic.test.student.entity;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.dto.TestEntity;

import javax.persistence.*;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "student_test_results", schema = "public")
public class StudentResultEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "result")
    private Integer result;

    @ManyToOne
    private TestEntity testEntity;

    @ManyToOne
    private UserAccount student;

    public Integer getId() {
        return id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public TestEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        this.testEntity = testEntity;
    }

    public UserAccount getStudent() {
        return student;
    }

    public void setStudent(UserAccount student) {
        this.student = student;
    }
}
