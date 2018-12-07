package com.tiwbnb.api.domains;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the transactions database table.
 * 
 */

public class TransactionRequest implements Serializable {
        

		private static final long serialVersionUID = 1L;

		@Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="id")
        private Long id;

        @Temporal(TemporalType.DATE)
        private Date cardDate;

        @Column(name="card_num", columnDefinition = "VARCHAR(16)", nullable = false)
        private String cardNum;

        private int cv2;

        @Temporal(TemporalType.DATE)
        private Date endDate;

        @Temporal(TemporalType.DATE)
        private Date startDate;

        private Long house;

        private Long invoiced;

        public TransactionRequest() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Date getCardDate() {
                return cardDate;
        }

        public void setCardDate(Date cardDate) {
                this.cardDate = cardDate;
        }

        public String getCardNum() {
                return cardNum;
        }

        public void setCardNum(String cardNum) {
                this.cardNum = cardNum;
        }
        

        public Date getEndDate() {
            return endDate;
	    }
	
	    public void setEndDate(Date endDate) {
	            this.endDate = endDate;
	    }
	    
	    public Date getStartDate() {
            return endDate;
	    }
	
	    public void setStartDate(Date startDate) {
	            this.startDate = startDate;
	    }
	    public Long getHouse(){
	    	return this.house;
	    }
	    public Long getInvoiced(){
	    	return this.invoiced;
	    }
	    
	    public int getCV2(){
	    	return this.cv2;
	    }
}
