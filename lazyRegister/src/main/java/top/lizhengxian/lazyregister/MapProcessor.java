package top.lizhengxian.lazyregister;

import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import top.lizhengxian.annotation.Subscribe;

@AutoService(Processor.class)
@SupportedOptions(value = {MapProcessor.EVENT_BUS_INDEX})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MapProcessor extends AbstractProcessor {
    static final String EVENT_BUS_INDEX = "eventBusIndex";
    private static final Class SUBSCRIBE = Subscribe.class;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(SUBSCRIBE.getCanonicalName());
        return types;
    }

    /**
     * Index配置中的Package名字
     */
    private String mIndexPackageName;

    /**
     * 一个Message只能发给一个类中的某一个方法
     */
    private  Map<Integer,Map<String,String>> mMsgId_Class_Method = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("init "+SUBSCRIBE.getCanonicalName());
        Map<String, String> options = processingEnv.getOptions();
        String className = options.get(EVENT_BUS_INDEX);
        mIndexPackageName = className.substring(0, className.lastIndexOf('.'));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        for (TypeElement annotation : annotations) {
//            System.out.println("process "+annotation.getClass());
//        }
            for (TypeElement annotation : annotations) {
                System.out.println(annotation.getClass());
                if (SUBSCRIBE.equals(annotation.getClass())) {
                    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
                    for (Element element : elements) {
                        System.out.println(element.getSimpleName()+":"+element.getEnclosingElement().asType()+
                                ":"+element.getAnnotation(SUBSCRIBE));
                    }
                }
            }
        return false;
    }

//    private void generateJavaClass() {
//        for (TypeElement enclosedElem : mBindViewElems.keySet()) {
//            //generate bind method
//            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("bind")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                    .addParameter(ClassName.get(enclosedElem.asType()),"activity")
//                    .returns(TypeName.VOID);
//
//            BindLayout bindLayoutAnno = enclosedElem.getAnnotation(BindLayout.class);
//            if (bindLayoutAnno != null){
//                methodSpecBuilder.addStatement(String.format(Locale.US,"activity.setContentView(%d)",bindLayoutAnno.value()));
//            }
//
//            for (Element bindElem : mBindViewElems.get(enclosedElem)) {
//                methodSpecBuilder.addStatement(String.format(Locale.US,"activity.%s = (%s)activity.findViewById(%d)",bindElem.getSimpleName(),bindElem.asType(),bindElem.getAnnotation(BindView.class).value()));
//            }
//            TypeSpec typeSpec = TypeSpec.classBuilder("Bind"+enclosedElem.getSimpleName())
//                    .superclass(TypeName.get(enclosedElem.asType()))
//                    .addModifiers(Modifier.FINAL,Modifier.PUBLIC)
//                    .addMethod(methodSpecBuilder.build())
//                    .build();
//            JavaFile file = JavaFile.builder(getPackageName(enclosedElem),typeSpec).build();
//            try {
//                file.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
////                e.printStackTrace();
//            }
//        }
//    }
}
