package com.techvg.shrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.shrms.domain.Holidays} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HolidaysDTO implements Serializable {

    private Long id;

    @NotNull
    private String holidayName;

    private Instant holidayDate;

    private String day;

    private Instant year;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean deleted;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Instant getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Instant holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Instant getYear() {
        return year;
    }

    public void setYear(Instant year) {
        this.year = year;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HolidaysDTO)) {
            return false;
        }

        HolidaysDTO holidaysDTO = (HolidaysDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, holidaysDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HolidaysDTO{" +
            "id=" + getId() +
            ", holidayName='" + getHolidayName() + "'" +
            ", holidayDate='" + getHolidayDate() + "'" +
            ", day='" + getDay() + "'" +
            ", year='" + getYear() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", company=" + getCompany() +
            "}";
    }
}
