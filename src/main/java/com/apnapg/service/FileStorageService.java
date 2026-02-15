//package com.apnapg.service;
//
//import org.springframework.web.multipart.MultipartFile;
//
//public interface FileStorageService {
//
//    String store(MultipartFile file);   // returns file URL/path
//
//    void delete(String fileUrl);
//}
package com.apnapg.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file);

    void delete(String fileName);
}
