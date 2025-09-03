export interface Port {
  id: string;
  portName: string;
  countryName: string;
  countryNameAbbreviation: string;
  portCode: string;
  portLongitude: number;
  portLatitude: number;
  portLogo: string;
  obs: string; // Optional field
}
