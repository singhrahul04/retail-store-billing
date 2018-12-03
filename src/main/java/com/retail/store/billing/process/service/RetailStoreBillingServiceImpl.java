package com.retail.store.billing.process.service;

import java.util.Arrays;

import com.retail.store.billing.discount.Discount;
import com.retail.store.billing.exception.RetailBillingTransactionException;
import com.retail.store.billing.model.Customer;
import com.retail.store.billing.model.CustomerType;
import com.retail.store.billing.process.RetailStoreBilling;
import com.retail.store.billing.process.RetailStoreBillingRepository;

public class RetailStoreBillingServiceImpl implements RetailStoreBillingService{
	
	@Override
	public Double netPayableAmt(Customer customer)
	{
		
		    RetailStoreBilling retailStoreBilling=new RetailStoreBillingRepository();
		    
		    if (customer.getBilledAmount()==null||customer.getBilledAmount() <= 0.0 ) {
	            throw new RetailBillingTransactionException(
	                    "Billed amount should be a positive number.",EXIT_STATUS_BILLED_AMOUNT_NOT_CORRECT);
	        }
		    if (customer.getCustomerType() ==null || !Arrays.asList(CustomerType.values()).contains(customer.getCustomerType())) {
	            throw new RetailBillingTransactionException(
	                    "Customer Type should be correct.",EXIT_STATUS_CUSTOMER_TYPE_NOT_CORRECT);
	        }
		    if (customer.getTransactionNumber() ==null ) {
	            throw new RetailBillingTransactionException(
	                    "Transaction Number should not be blank.",EXIT_STATUS_TRANSACTION_NO_NOT_CORRECT);
	        }
			try{
			Discount<Customer>  discount=(Customer customerx)-> {
			if(customer.getCustomerType().equals(CustomerType.STORE_EMPLOYEE))
					return ()->((30*customer.getBilledAmount())/100)+ ((customer.getBilledAmount()/100)*5);
			else if(customer.getCustomerType().equals(CustomerType.AFFILATED_CUSTOMER))
					return ()->((10*customer.getBilledAmount())/100)+ ((customer.getBilledAmount()/100)*5);
			else if(customer.getCustomerType().equals(CustomerType.CUSTOMER_OVER_2_YEARS))
					return ()->((5*customer.getBilledAmount())/100)+ ((customer.getBilledAmount()/100)*5);
			else if(customer.getCustomerType().equals(CustomerType.GROCEIES))
				    return ()->(customer.getBilledAmount()/100)*5;
		    else								
				    return ()->0.0;
				};
			
		  retailStoreBilling.netPayable(customer, discount);
			}catch(Exception e){
				throw new RetailBillingTransactionException(
	                    "Transaction failed!!!!",EXIT_STATUS_TRANSCATION_FAILED);
	        	
				
			}
		  
		return customer.getNetPayableAmount();
	}
	
	

}
