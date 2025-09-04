export interface Port {
  ISO3: string;
  COUNTRY: string;
  PORTNAME: string;
  CODEPORT: string;
  LATITUDE: string;
  LONGITUDE: string;
  LOGO: string;
}

export interface Transportation {
  ID: string;
  TRANSPORTATIONNAME: string;
}

export interface Container {
  ID: string;
  CONTAINERNAME: string;
  CONTAINERWEIGHT: number;
}
