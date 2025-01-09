package org.zerock.apiserver.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.MemberRole;
import org.zerock.apiserver.security.dto.CustomUserDetails;
import org.zerock.apiserver.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        // Preflight 요청은 체크하지 않음
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String path = request.getRequestURI();
        log.info("check uri.............." + path);

        if (request.getMethod().equals("POST")) {
            if (path.equals("/api/member/") || path.equals("/api/member/login")) {
                return true;
            }
        } else if (request.getMethod().equals("GET")) {
            if (path.startsWith("/api/member/")) {
                return true;
            } else if (path.startsWith("/api/profiles/image/view/")) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("-----------------JWTCheckFilter-----------------");
        String authHeaderStr = request.getHeader("Authorization");
        try {
            // Bearer accessToken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            long mno = Long.parseLong(claims.get("mno").toString());
            String email = (String) claims.get("email");
            String nickname = (String) claims.get("nickname");
            Boolean social = (Boolean) claims.get("social");
            String role = (String) claims.get("role");
            MemberRole memberRole = MemberRole.valueOf(role);

            Member member = Member.builder()
                    .mno(mno)
                    .email(email)
                    .nickname(nickname)
                    .social(social)
                    .memberRole(memberRole)
                    .build();
            log.info("Member: " + member);

            CustomUserDetails customUserDetails = new CustomUserDetails(member);

            log.info("-----------------------------------");
            log.info(customUserDetails);
            log.info(customUserDetails.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch(Exception e) {
            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }

}
