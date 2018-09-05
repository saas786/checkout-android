/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.model;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * Payment plan to pay by schedule.
 */
public class InstallmentsPlan {
	/** unique plan id. */
	private String id;
	/** Collection of installment's info (mandatory) */
	private List<InstallmentItem> schedule;
	/** An array of possible payment days (optional) */
	private List<Integer> dueDays;

	/** Currency value (mandatory) */
	private String currency;

	/** The interest amount. */
	private BigDecimal interestAmount;
	/** The fee for the installment payment (or serviceChargeAmount) (mandatory) */
	private BigDecimal installmentFee;
	/** The total transaction amount after calculation including all fees and interest (mandatory) */
	private BigDecimal totalAmount;

	/** The interest rate per year in percentages (Nominalzins or Sollzins) (mandatory) */
	private BigDecimal nominalInterestRate;
	/** The effective interest rate per year in percentages (Effektivzins) (mandatory) */
	private BigDecimal effectiveInterestRate;

	/** An URL to the Credit Information document (optional) */
	private URL creditInformationUrl;
	/** An URL to terms and conditions information document (optional) */
	private URL termsAndConditionsUrl;
	/** An URL to the data privacy consent document (optional) */
	private URL dataPrivacyConsentUrl;

	/**
	 * Sets plan id.
	 *
	 * @param id Plan id.
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Gets plan id.
	 *
	 * @return Plan id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets collection of particular schedule.
	 *
	 * @return Installments information.
	 */
	public List<InstallmentItem> getSchedule() {
		return schedule;
	}

	/**
	 * Sets schedule information.<br>
	 * This information should be generated by adapter if it is not provided by PSP: use schedule count and due amount to do this.
	 *
	 * @param schedule Collection of schedule information.
	 */
	public void setSchedule(final List<InstallmentItem> schedule) {
		this.schedule = schedule;
	}

	/**
	 * Gets currency.
	 *
	 * @return Currency value.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets currency.
	 *
	 * @param currency Currency value.
	 */
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	/**
	 * Gets total amount what customer should pay.
	 *
	 * @return Amount value in major units.
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets total amount what customer should pay by this plan.
	 *
	 * @param totalAmount Amount value in major units.
	 */
	public void setTotalAmount(final BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets service fee for this schedule plan.
	 *
	 * @return Amount value in major units.
	 */
	public BigDecimal getInstallmentFee() {
		return installmentFee;
	}

	/**
	 * Sets service fee for this schedule plan.
	 *
	 * @param installmentFee Amount value in major units.
	 */
	public void setInstallmentFee(final BigDecimal installmentFee) {
		this.installmentFee = installmentFee;
	}

	/**
	 * Gets interest rate per year in percentages (Nominalzins or Sollzins).
	 *
	 * @return Rate in percentages.
	 */
	public BigDecimal getNominalInterestRate() {
		return nominalInterestRate;
	}

	/**
	 * Sets interest rate per year in percentages.
	 *
	 * @param nominalInterestRate Rate in percentages.
	 */
	public void setNominalInterestRate(final BigDecimal nominalInterestRate) {
		this.nominalInterestRate = nominalInterestRate;
	}

	/**
	 * Gets the effective interest rate per year in percentages (Effektivzins).
	 *
	 * @return Rate in percentages.
	 */
	public BigDecimal getEffectiveInterestRate() {
		return effectiveInterestRate;
	}

	/**
	 * Sets the effective interest rate per year in percentages.
	 *
	 * @param effectiveInterestRate Rate in percentages.
	 */
	public void setEffectiveInterestRate(final BigDecimal effectiveInterestRate) {
		this.effectiveInterestRate = effectiveInterestRate;
	}

	/**
	 * Gets an URL to the Credit Information document.
	 *
	 * @return URL object.
	 */
	public URL getCreditInformationUrl() {
		return creditInformationUrl;
	}

	/**
	 * Sets an URL to the Credit Information document.
	 *
	 * @param creditInformationUrl URL object.
	 */
	public void setCreditInformationUrl(final URL creditInformationUrl) {
		this.creditInformationUrl = creditInformationUrl;
	}

	/**
	 * Gets an URL to terms and conditions information document.
	 *
	 * @return URL object.
	 */
	public URL getTermsAndConditionsUrl() {
		return termsAndConditionsUrl;
	}

	/**
	 * Sets an URL to terms and conditions information document.
	 *
	 * @param termsAndConditionsUrl URL object.
	 */
	public void setTermsAndConditionsUrl(final URL termsAndConditionsUrl) {
		this.termsAndConditionsUrl = termsAndConditionsUrl;
	}

	/**
	 * Gets an URL to the data privacy consent document.
	 *
	 * @return URL object.
	 */
	public URL getDataPrivacyConsentUrl() {
		return dataPrivacyConsentUrl;
	}

	/**
	 * Sets an URL to the data privacy consent document.
	 *
	 * @param dataPrivacyConsentUrl URL object.
	 */
	public void setDataPrivacyConsentUrl(final URL dataPrivacyConsentUrl) {
		this.dataPrivacyConsentUrl = dataPrivacyConsentUrl;
	}

	/**
	 * Sets interest amount.
	 *
	 * @param interestAmount Amount value in major units.
	 */
	public void setInterestAmount(final BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}

	/**
	 * Gets interest amount.
	 *
	 * @return Amount value in major units.
	 */
	public BigDecimal getInterestAmount() {
		return interestAmount;
	}

	/**
	 * Sets a collection of possible payment days provided by provider.
	 *
	 * @param dueDays A collection with calendar days.
	 */
	public void setDueDays(final List<Integer> dueDays) {
		this.dueDays = dueDays;
	}

	/**
	 * Gets a collection of possible payment days provided by provider.
	 *
	 * @return A collection object with calendar days.
	 */
	public List<Integer> getDueDays() {
		return dueDays;
	}
}
