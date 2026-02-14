////package com.apnapg.config;
////
////import org.mapstruct.MapperConfig;
////import org.mapstruct.NullValuePropertyMappingStrategy;
////import org.mapstruct.NullValueCheckStrategy;
////import org.mapstruct.ReportingPolicy;
////
////@MapperConfig(
////        componentModel = "spring",
////        unmappedTargetPolicy = ReportingPolicy.ERROR,
////        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
////        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
////)
////public interface CentralMapperConfig {
////}
//package com.apnapg.config;
//
//import org.mapstruct.MapperConfig;
//import org.mapstruct.NullValueCheckStrategy;
//import org.mapstruct.NullValuePropertyMappingStrategy;
//import org.mapstruct.ReportingPolicy;
//
//@MapperConfig(
//        componentModel = "spring",
//        unmappedTargetPolicy = ReportingPolicy.IGNORE, // 🔥 IMPORTANT FIX
//        nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
//        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
//)
//public interface CentralMapperConfig {
//}
package com.apnapg.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Builder;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true) // 🔥 MUST BE PRESENT
)
public interface CentralMapperConfig {
}
