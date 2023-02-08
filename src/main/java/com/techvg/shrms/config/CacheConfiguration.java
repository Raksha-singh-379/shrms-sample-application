package com.techvg.shrms.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.techvg.shrms.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.techvg.shrms.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.techvg.shrms.domain.User.class.getName());
            createCache(cm, com.techvg.shrms.domain.Authority.class.getName());
            createCache(cm, com.techvg.shrms.domain.User.class.getName() + ".authorities");
            createCache(cm, com.techvg.shrms.domain.Employee.class.getName());
            createCache(cm, com.techvg.shrms.domain.EmployeeDetails.class.getName());
            createCache(cm, com.techvg.shrms.domain.Address.class.getName());
            createCache(cm, com.techvg.shrms.domain.FamilyInfo.class.getName());
            createCache(cm, com.techvg.shrms.domain.Contacts.class.getName());
            createCache(cm, com.techvg.shrms.domain.BanksDetails.class.getName());
            createCache(cm, com.techvg.shrms.domain.Education.class.getName());
            createCache(cm, com.techvg.shrms.domain.WorkExperience.class.getName());
            createCache(cm, com.techvg.shrms.domain.Projects.class.getName());
            createCache(cm, com.techvg.shrms.domain.ProjectTeams.class.getName());
            createCache(cm, com.techvg.shrms.domain.EmpSalaryInfo.class.getName());
            createCache(cm, com.techvg.shrms.domain.Company.class.getName());
            createCache(cm, com.techvg.shrms.domain.Region.class.getName());
            createCache(cm, com.techvg.shrms.domain.Branch.class.getName());
            createCache(cm, com.techvg.shrms.domain.State.class.getName());
            createCache(cm, com.techvg.shrms.domain.District.class.getName());
            createCache(cm, com.techvg.shrms.domain.Taluka.class.getName());
            createCache(cm, com.techvg.shrms.domain.City.class.getName());
            createCache(cm, com.techvg.shrms.domain.SalarySettings.class.getName());
            createCache(cm, com.techvg.shrms.domain.Department.class.getName());
            createCache(cm, com.techvg.shrms.domain.Designation.class.getName());
            createCache(cm, com.techvg.shrms.domain.LeaveType.class.getName());
            createCache(cm, com.techvg.shrms.domain.LeavePolicy.class.getName());
            createCache(cm, com.techvg.shrms.domain.LeaveApplication.class.getName());
            createCache(cm, com.techvg.shrms.domain.LeaveTranscation.class.getName());
            createCache(cm, com.techvg.shrms.domain.Holidays.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
