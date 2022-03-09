package application.model;

import com.ibm.cloud.cloudant.v1.model.*;

import java.util.List;
import java.util.Map;

public class ModelHelper {

    public PostFindOptions getFindOptions(Map<String, Object> selector, String database) {
        PostFindOptions findOptions = new PostFindOptions.Builder()
                .db(database)
                .selector(selector)
                .build();
        return findOptions;
    }

    public DeleteDocumentOptions getDeleteDocumentOptions(String id, String revId, String database) {
        DeleteDocumentOptions deleteDocumentOptions =
                new DeleteDocumentOptions.Builder()
                        .db(database)
                        .rev(revId)
                        .docId(id)
                        .build();
        return deleteDocumentOptions;
    }

    public GetDocumentOptions getGetDocumentOptions(String id, String database) {
        GetDocumentOptions getDocOptions = new GetDocumentOptions.Builder()
                .db(database)
                .docId(id)
                .build();
        return getDocOptions;
    }

    public PostDocumentOptions getPostDocumentOptions(Document document, String database) {
        PostDocumentOptions documentOptions =
                new PostDocumentOptions.Builder()
                        .db(database)
                        .document(document)
                        .build();
        return documentOptions;
    }
}
