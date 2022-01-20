package com.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.DELETE;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.customer.Address;
import com.mercadopago.resources.datastructures.customer.DefaultAddress;
import com.mercadopago.resources.datastructures.customer.Identification;
import com.mercadopago.resources.datastructures.customer.Phone;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * This class allows you to store customers data safely to improve the shopping experience.
 * This will allow your customer to complete their purchases much faster and easily when used in conjunction with the Cards class.
 */
public class Customer extends MPBase {

    private String id = null;
    private String email = null;
    private String firstName = null;
    private String lastName = null;
    private Phone phone = null;
    private Identification identification = null;
    private String defaultAddress = null;
    private DefaultAddress address = null;
    private Date dateRegistered = null;
    private String description = null;
    private Date dateCreated = null;
    private Date dateLastUpdated = null;
    private JsonObject metadata = null;
    private String defaultCard = null;
    private ArrayList<Card> cards = null;
    private ArrayList<Address> addresses = null;
    private Boolean liveMode = null;

    /**
     * @return customer ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email
     * @return the customer
     */
    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName first name
     * @return the customer
     */
    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName last name
     * @return the customer
     */
    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return phone information
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * @param phone phone information
     * @return the customer
     */
    public Customer setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    /**
     * @return identification information
     */
    public Identification getIdentification() {
        return identification;
    }

    /**
     * @param identification identification information
     * @return the customer
     */
    public Customer setIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }

    /**
     * @return default address
     */
    public String getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * @param defaultAddress default address
     * @return the customer
     */
    public Customer setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
        return this;
    }

    /**
     * @return address information
     */
    public DefaultAddress getAddress() {
        return address;
    }

    /**
     * @param address address information
     * @return the customer
     */
    public Customer setAddress(DefaultAddress address) {
        this.address = address;
        return this;
    }

    /**
     * @return date of registration
     */
    public Date getDateRegistered() {
        return dateRegistered;
    }

    /**
     * @param dateRegistered date of registration
     * @return the customer
     */
    public Customer setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
        return this;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description description
     * @return the customer
     */
    public Customer setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return date of last updated
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * @return metadata information
     */
    public JsonObject getMetadata() {
        return metadata;
    }

    /**
     * @param metadata metadata information
     * @return the customer
     */
    public Customer setMetadata(JsonObject metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * @return default card
     */
    public String getDefaultCard() {
        return defaultCard;
    }

    /**
     * @param defaultCard default card
     * @return the customer
     */
    public Customer setDefaultCard(String defaultCard) {
        this.defaultCard = defaultCard;
        return this;
    }

    /**
     * @return cards from customer
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * @return addresses from customer
     */
    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    /**
     * @return true if has live mode, otherwise false
     */
    public Boolean getLiveMode() {
        return liveMode;
    }

    /**
     * Searches the customers acoording to specific filters.
     * @param filters search filters
     * @param useCache true if will use cache, otherwise false
     * @return the searched customers
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray search(HashMap<String, String> filters, Boolean useCache) throws MPException {
    return search(filters, useCache, MPRequestOptions.builder().build());
    }

    /**
     * Searches the customers acoording to specific filters.
     * @param filters search filters
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the searched customers
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/customers/search")
    public static MPResourceArray search(HashMap<String, String> filters, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Customer.class, "search", filters, useCache, requestOptions);
    }

    /**
     * Finds a customer by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/get/">api docs</a>
     * @param id customer ID
     * @return the customer
     * @throws MPException an error if the request fails
     */
    public static Customer findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    }

    /**
     * Finds a customer by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/get/">api docs</a>
     * @param id customer ID
     * @param useCache true if will use cache, otherwise false
     * @return the customer
     * @throws MPException an error if the request fails
     */
    public static Customer findById(String id, Boolean useCache) throws MPException {
    return findById(id, useCache, MPRequestOptions.builder().build());
    }

    /**
     * Finds a customer by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/get/">api docs</a>
     * @param id customer ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the customer
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/customers/:id")
    public static Customer findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(Customer.class, "findById", useCache, requestOptions, id);
    }

    /**
     * Saves the new customer
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers/post/">api docs</a>
     * @return the saved customer
     * @throws MPException an error if the request fails
     */
    public Customer save() throws MPException {
    return save(MPRequestOptions.builder().build());
    }

    /**
     * Saves the new customer
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers/post/">api docs</a>
     * @param requestOptions request options
     * @return the saved customer
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/customers")
    public Customer save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Updates the customer editable properties
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/put/">api docs</a>
     * @return the updated customer
     * @throws MPException an error if the request fails
     */
    public Customer update() throws MPException {
    return update(MPRequestOptions.builder().build());
    }

    /**
     * Updates the customer editable properties
     * @see <a href="https://www.mercadopago.com/developers/en/reference/customers/_customers_id/put/">api docs</a>
     * @param requestOptions request options
     * @return the updated customer
     * @throws MPException an error if the request fails
     */
    @PUT(path="/v1/customers/:id")
    public Customer update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Deletes the customer
     * @return the deleted customer
     * @throws MPException an error if the request fails
     */
    public Customer delete() throws MPException {
    return delete(MPRequestOptions.builder().build());
    }

    /**
     * Deletes the customer
     * @param requestOptions request options
     * @return the deleted customer
     * @throws MPException an error if the request fails
     */
    @DELETE(path="/v1/customers/:id")
    public Customer delete(MPRequestOptions requestOptions) throws MPException {
        return processMethod("delete", WITHOUT_CACHE, requestOptions);
    }

}
