////package com.apnapg.service;
////import com.apnapg.dto.pg.*;
////import java.util.List;
////public interface PGService {
////    PGResponse createPG(PGCreateRequest dto, Long ownerId);
////    PGResponse updatePG(Long pgId, PGCreateRequest dto);
////    void deletePG(Long pgId);
////    PGResponse getPGDetails(Long pgId);
////    List<PGResponse> searchPG(String city, Double minRent, Double maxRent);
////}
//package com.apnapg.service;
//import com.apnapg.dto.pagination.PageResponseDTO;
//import com.apnapg.dto.pg.*;
//public interface PGService {
//
//    // Owner creates PG
//    PGResponse createPG(PGCreateRequest request, Long ownerId);
//    // Owner updates PG
//    PGResponse updatePG(Long pgId, PGCreateRequest request);
//
//    // ======================================================
//    // UPDATE PG (Owner scoped)
//    // ======================================================
//   // PGResponse updatePG(Long pgId, PGCreateRequest dto, Long ownerId);
//
//    // Get single PG details
//    PGResponse getPG(Long pgId);
//    // Delete PG (only if no active rooms)
//    void deletePG(Long pgId);
//
//    // ======================================================
//    // DELETE PG (Owner scoped + safety)
//    // ======================================================
//   // void deletePG(Long pgId, Long ownerId);
//
//    // Search / list PGs with pagination
//    PageResponseDTO<PGResponse> searchPGs(
//            PGSearchDTO searchDTO,
//            int page,
//            int size
//    );
//}
package com.apnapg.service;

import com.apnapg.dto.pagination.PageResponseDTO;
import com.apnapg.dto.pg.PGCreateRequest;
import com.apnapg.dto.pg.PGResponse;
import com.apnapg.dto.pg.PGSearchDTO;

public interface PGService {

    // Create PG (Owner scoped)
    PGResponse createPG(PGCreateRequest request, Long ownerId);

    // Update PG (Owner scoped)
    PGResponse updatePG(Long pgId, PGCreateRequest request, Long ownerId);

    // Get single PG
    PGResponse getPG(Long pgId);

    // Delete PG (Owner scoped + safety check)
    void deletePG(Long pgId, Long ownerId);

    // Search PGs with pagination
    PageResponseDTO<PGResponse> searchPGs(
            PGSearchDTO searchDTO,
            int page,
            int size
    );

    void addImageToPG(Long pgId, Long ownerId, String fileName);

}
