package com.example.project531.responsedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseCustomer {

@SerializedName("id")
@Expose
private String id;
@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("customer_name")
@Expose
private String customerName;
@SerializedName("address")
@Expose
private String address;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("password")
@Expose
private String password;
@SerializedName("email")
@Expose
private String email;
@SerializedName("del_route")
@Expose
private String delRoute;
@SerializedName("ref_name")
@Expose
private String refName;
@SerializedName("prevoius_due")
@Expose
private String prevoiusDue;
@SerializedName("br")
@Expose
private String br;
@SerializedName("post_date")
@Expose
private String postDate;
@SerializedName("invoice")
@Expose
private String invoice;
@SerializedName("status")
@Expose
private String status;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getCustomerName() {
return customerName;
}

public void setCustomerName(String customerName) {
this.customerName = customerName;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getDelRoute() {
return delRoute;
}

public void setDelRoute(String delRoute) {
this.delRoute = delRoute;
}

public String getRefName() {
return refName;
}

public void setRefName(String refName) {
this.refName = refName;
}

public String getPrevoiusDue() {
return prevoiusDue;
}

public void setPrevoiusDue(String prevoiusDue) {
this.prevoiusDue = prevoiusDue;
}

public String getBr() {
return br;
}

public void setBr(String br) {
this.br = br;
}

public String getPostDate() {
return postDate;
}

public void setPostDate(String postDate) {
this.postDate = postDate;
}

public String getInvoice() {
return invoice;
}

public void setInvoice(String invoice) {
this.invoice = invoice;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}