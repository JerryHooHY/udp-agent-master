/*
 * MATLAB Compiler: 24.2 (R2024b)
 * Date: Mon Dec 16 17:25:12 2024
 * Arguments: 
 * "-B""macro_default""-W""java:entranceinfo,EntranceInfo""-T""link:lib""-d""C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\entranceinfo\\for_testing""class{EntranceInfo:C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\assess_external_danger.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\calculate_weighted_score.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\classify_weapon.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\head_performance_evaluation.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\main_classify_weapon.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\process_sensor_data.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\PSO.m,C:\\Users\\15854\\Desktop\\udp-agent-master\\mcode\\all\\PSO_Location_and_WeaponEquivalent.m}"
 */

package entranceinfo;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.io.Serializable;
/**
 * <i>INTERNAL USE ONLY</i>
 */
public class EntranceinfoMCRFactory implements Serializable 
{
    /** Component's uuid */
    private static final String sComponentId = "entranceinfo_92f00e28-4487-477b-95bc-60a2b20d73ec";
    
    /** Component name */
    private static final String sComponentName = "entranceinfo";
    
   
    /** Pointer to default component options */
    private static final MWComponentOptions sDefaultComponentOptions = 
        new MWComponentOptions(
            MWCtfExtractLocation.EXTRACT_TO_CACHE, 
            new MWCtfClassLoaderSource(EntranceinfoMCRFactory.class)
        );
    
    
    private EntranceinfoMCRFactory()
    {
        // Never called.
    }
    
    /**
     * Create a MWMCR instance with the required options.
     * @param componentOptions Options applied to the component.
     * @return A shared MCR instance
     * @throws MWException An error has occurred during the function call.
     */
    public static MWMCR newInstance(MWComponentOptions componentOptions) throws MWException
    {
        if (null == componentOptions.getCtfSource()) {
            componentOptions = new MWComponentOptions(componentOptions);
            componentOptions.setCtfSource(sDefaultComponentOptions.getCtfSource());
        }
        return MWMCR.newInstance(
            componentOptions, 
            EntranceinfoMCRFactory.class, 
            sComponentName, 
            sComponentId,
            new int[]{24,2,0}
        );
    }
    
    /**
     * Create a MWMCR instance with the default options
     * @return A MCR instance
     * @throws MWException An error has occurred during the function call.
     */
    public static MWMCR newInstance() throws MWException
    {
        return newInstance(sDefaultComponentOptions);
    }
}
