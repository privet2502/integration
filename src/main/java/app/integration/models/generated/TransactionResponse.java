//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.10.03 at 06:56:39 PM ALMT 
//


package app.integration.models.generated;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


/**
 * <p>Java class for TransactionResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TransactionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="account_from" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="account_to" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="currency_shortname" type="{http://iitu.kz/transaction}CurrencyType"/&gt;
 *         &lt;element name="sum" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="expense_category" type="{http://iitu.kz/transaction}ExpenseCategory"/&gt;
 *         &lt;element name="datetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="limit_sum" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="limit_datetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="limit_currency_shortname" type="{http://iitu.kz/transaction}CurrencyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionResponse", propOrder = {
    "accountFrom",
    "accountTo",
    "currencyShortname",
    "sum",
    "expenseCategory",
    "datetime",
    "limitSum",
    "limitDatetime",
    "limitCurrencyShortname"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    @XmlElement(name = "account_from", required = true)
    protected String accountFrom;
    @XmlElement(name = "account_to", required = true)
    protected String accountTo;
    @XmlElement(name = "currency_shortname", required = true)
    @XmlSchemaType(name = "string")
    protected CurrencyType currencyShortname;
    protected double sum;
    @XmlElement(name = "expense_category", required = true)
    @XmlSchemaType(name = "string")
    protected ExpenseCategory expenseCategory;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datetime;
    @XmlElement(name = "limit_sum")
    protected double limitSum;
    @XmlElement(name = "limit_datetime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitDatetime;
    @XmlElement(name = "limit_currency_shortname", required = true)
    @XmlSchemaType(name = "string")
    protected CurrencyType limitCurrencyShortname;

    /**
     * Gets the value of the accountFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountFrom() {
        return accountFrom;
    }

    /**
     * Sets the value of the accountFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountFrom(String value) {
        this.accountFrom = value;
    }

    /**
     * Gets the value of the accountTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountTo() {
        return accountTo;
    }

    /**
     * Sets the value of the accountTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountTo(String value) {
        this.accountTo = value;
    }

    /**
     * Gets the value of the currencyShortname property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyType }
     *     
     */
    public CurrencyType getCurrencyShortname() {
        return currencyShortname;
    }

    /**
     * Sets the value of the currencyShortname property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyType }
     *     
     */
    public void setCurrencyShortname(CurrencyType value) {
        this.currencyShortname = value;
    }

    /**
     * Gets the value of the sum property.
     * 
     */
    public double getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     * 
     */
    public void setSum(double value) {
        this.sum = value;
    }

    /**
     * Gets the value of the expenseCategory property.
     * 
     * @return
     *     possible object is
     *     {@link ExpenseCategory }
     *     
     */
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    /**
     * Sets the value of the expenseCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpenseCategory }
     *     
     */
    public void setExpenseCategory(ExpenseCategory value) {
        this.expenseCategory = value;
    }

    /**
     * Gets the value of the datetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatetime() {
        return datetime;
    }

    /**
     * Sets the value of the datetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatetime(XMLGregorianCalendar value) {
        this.datetime = value;
    }

    /**
     * Gets the value of the limitSum property.
     * 
     */
    public double getLimitSum() {
        return limitSum;
    }

    /**
     * Sets the value of the limitSum property.
     * 
     */
    public void setLimitSum(double value) {
        this.limitSum = value;
    }

    /**
     * Gets the value of the limitDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLimitDatetime() {
        return limitDatetime;
    }

    /**
     * Sets the value of the limitDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLimitDatetime(XMLGregorianCalendar value) {
        this.limitDatetime = value;
    }

    /**
     * Gets the value of the limitCurrencyShortname property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyType }
     *     
     */
    public CurrencyType getLimitCurrencyShortname() {
        return limitCurrencyShortname;
    }

    /**
     * Sets the value of the limitCurrencyShortname property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyType }
     *     
     */
    public void setLimitCurrencyShortname(CurrencyType value) {
        this.limitCurrencyShortname = value;
    }

}