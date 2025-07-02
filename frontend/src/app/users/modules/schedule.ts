export interface Schedule {
  id: string;
  portFromId: string;
  portToId: string;
  companyId: string;
  portFromName: string;
  portToName: string;
  companyName: string;
  dateDepart: string;
  dateArrive: string;
  transit: number;
  vessel: string;
  refVoyage: string;
  serviceName: string;
  active: number;
}
