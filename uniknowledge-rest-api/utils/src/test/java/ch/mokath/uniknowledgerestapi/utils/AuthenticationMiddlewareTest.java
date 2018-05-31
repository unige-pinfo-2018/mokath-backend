package ch.mokath.uniknowledgerestapi.utils;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/*import org.junit.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ch.mokath.uniknowledgerestapi.utils.CustomException;
//import org.glassfish.jersey.server.ContainerRequest.java;

/**
* @author ornela
* @author zue
*/
//@RunWith(MockitoJUnitRunner.class)
public class AuthenticationMiddlewareTest {

	private static final String AUTHENTICATION_SCHEME = "Bearer";
/*/public static final ThreadLocal<ContainerRequestContext> requestContextHolder;
    @InjectMocks
    private AccessTokenFilter filter;

    @Mock
    private Authorizable authorizer;

    @Mock
    private ContainerRequestContext crc;

    @Before
    private void setUp() {
        reset(crc);
    }

     @Test
    private void testFilter_TokenIsNonBlank_ShouldProceedTokenToAuthorizer() {
        when(crc.getHeaderString(AccessTokenFilter.TOKEN_NAME)).thenReturn("valid");

        filter.filter(crc);

        verify(authorizer).authorize("valid");
    }
    /* /
      @Test
    public void testFilter() {
 //   ContainerRequest cr = new ContainerRequest(null,"login","POST",SecurityContext.isSecure(),null);
        try{
            filter(null);
        }catch (IOException ioe){
        }
/*    ContainerRequestFilterImpl filter = new ContainerRequestFilterImpl( null );
    ContainerRequestContext context = mock( ContainerRequestContext.class );
   
    filter.filter( context );
   
    verify( context, never() ).setSecurityContext( any( SecurityContext.class ) ); * /
    }
   @Test
	public void ATest(){
//        Assert.assertFalse(isTokenBasedAuthentication(""));
/*        ContainerRequestContext requestContext;
        requestContext.setMethod("GET");
        Assert.assertTrue(true);
* /	}
*/

}
