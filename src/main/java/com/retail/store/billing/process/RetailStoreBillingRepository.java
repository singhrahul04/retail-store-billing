package com.retail.store.billing.process;

import com.retail.store.billing.discount.Discount;
import com.retail.store.billing.discount.DiscountCalculator;
import com.retail.store.billing.model.Customer;

public class RetailStoreBillingRepository implements RetailStoreBilling{
	
	@Override
	public Customer netPayable(Customer input, Discount<Customer> logic){
		
		return new RetailStoreBillingProcessor().execute(input, logic,  (Customer inputx, Discount<Customer> logicx)-> {
			
				  DiscountCalculator calculator=new DiscountCalculator(input);
				  Double totaldiscount=calculator.calculateDiscount(logic); 
				  Double netPayable=input.getBilledAmount()-totaldiscount; 
				  input.setNetPayableAmount(netPayable);
				  return input;
			
		});

	}

}
