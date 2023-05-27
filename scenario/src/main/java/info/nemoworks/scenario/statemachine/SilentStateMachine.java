package info.nemoworks.scenario.statemachine;

import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.model.ModelException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class SilentStateMachine extends AbstractStateMachine {

    public SilentStateMachine(URL scxmlDocument) throws ModelException {
        super(scxmlDocument);
    }

    /**
     * Keep the statemachine silent if a method is missing for a state
     *
     * @param methodName
     * @return
     */
    @Override
    public boolean invoke(String methodName) {
        Class clas = this.getClass();

        try {
            Method method = clas.getDeclaredMethod(methodName, new Class[0]);
            method.invoke(this, new Object[0]);
            return true;
        } catch (SecurityException var4) {
            this.logError(var4);
            return false;
        } catch (NoSuchMethodException var5) {
//            this.logError(var5);
            return false;
        } catch (IllegalArgumentException var6) {
            this.logError(var6);
            return false;
        } catch (IllegalAccessException var7) {
            this.logError(var7);
            return false;
        } catch (InvocationTargetException var8) {
            this.logError(var8);
            return false;
        }
    }

}
