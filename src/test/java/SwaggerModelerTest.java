import com.abandy.swagger.modeler.AutoRestModelBuilder;
import com.abandy.swagger.modeler.SwaggerModelBuilder;
import com.abandy.swagger.modeler.autorest.CodeModel;
import com.abandy.swagger.modeler.swagger.Swagger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class SwaggerModelerTest {
    private String testSwaggerUrl = "";

    @Test
    void TestSwaggerModel() {
        SwaggerModelBuilder swaggerModelBuilder =
                new SwaggerModelBuilder();
        try {
            List<Swagger> model = swaggerModelBuilder.buildModel(testSwaggerUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void TestCodeModel() {
        AutoRestModelBuilder autoRestModelBuilder =
                new AutoRestModelBuilder();
        String[] swaggerUrls =
                new String[] {
                    testSwaggerUrl
                };
        try {
            List<CodeModel> model = autoRestModelBuilder.buildModels(swaggerUrls);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
