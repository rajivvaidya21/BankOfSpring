package com.bankofspring.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.MappedSuperclass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents all common properties of standard domain objects
 *
 * @author Arpit Khandelwal
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@RunWith(SpringRunner.class)
public abstract class BaseDomainObjectTest implements Serializable {

	
    @CreationTimestamp
    protected Date createTimestamp = new Date();

    @UpdateTimestamp
    protected Date lastEditTimestamp;
    
    
    @Test
    public void dateTest() {
        final long doubleTime = createTimestamp.getTime();
        assertNotEquals(0, doubleTime);
    }
}
