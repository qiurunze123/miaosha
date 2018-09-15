package com.geekq.miaosha.validator;

import com.geekq.miaosha.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile , String> {

    private boolean require = false ;

    @Override
    public void initialize(IsMobile isMobile) {
        require = isMobile.required() ;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(require){
            return ValidatorUtil.isMobile(value) ;
        }else{
            if(StringUtils.isEmpty(value)){
                return true ;
            }else {
                return ValidatorUtil.isMobile(value) ;
            }
        }
    }
}
