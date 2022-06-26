package actor.system.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class LocalIpUtil {

	public static InetAddress getLocalHostLANAddress() throws Exception {
	    try {
	        InetAddress candidateAddress = null;
	        // 遍历所有的网络接口
	        for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
	            NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
	            // 在所有的接口下再遍历IP
	            for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
	                InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
	                if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
	                    if (inetAddr.isSiteLocalAddress()) {
	                        // 如果是site-local地址，就是它了
	                        return inetAddr;
	                    } else if (candidateAddress == null) {
	                        // site-local类型的地址未被发现，先记录候选地址
	                        candidateAddress = inetAddr;
	                    }
	                }
	            }
	        }
	        if (candidateAddress != null) {
	            return candidateAddress;
	        }
	        // 如果没有发现 non-loopback地址.只能用最次选的方案
	        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
	        return jdkSuppliedAddress;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static String getLocalIp() {
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
	        String siteLocalAddress = null;
	        while (ifaces.hasMoreElements()) {
	            NetworkInterface iface = ifaces.nextElement();
	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while (addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                String hostAddress = addr.getHostAddress();
	                if (addr instanceof Inet4Address
	                        && !addr.isLoopbackAddress()
	                        && !hostAddress.startsWith("192.168")
	                        && !hostAddress.startsWith("172.")
	                        && !hostAddress.startsWith("169.")) {
	                    if (addr.isSiteLocalAddress()) {
	                        siteLocalAddress = hostAddress;
	                    } else {
	                        return hostAddress;
	                    }
	                }
	            }
	        }
	        return siteLocalAddress == null ? "" : siteLocalAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
