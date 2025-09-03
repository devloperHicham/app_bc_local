export interface AuthTokens {
  time?: string;
  httpStatus?: string;
  isSuccess?: boolean;
  response?: {
    accessToken: string;
    accessTokenExpiresAt: number;
    refreshToken: string;
  };
  accessToken?: string;
  refreshToken?: string;
}

export interface DecodedToken {
  jti: string;
  iat: number;
  exp: number;
  userStatus: string;
  userLastName: string;
  userPhoneNumber: string;
  userEmail: string;
  userType: string; // This appears to be your "role"
  userFirstName: string;
  userId: string; // Note: This is different from 'sub'
  storeTitle: string;
  alg: string;
  typ: string;
  [key: string]: any;
}
