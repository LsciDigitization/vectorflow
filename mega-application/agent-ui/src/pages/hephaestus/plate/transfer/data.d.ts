
export interface TransferData  {
  id: string;
  planId: string;

  sourcePlate: string;
  sourcePlateKey: string,
  sourceWell: string;
  sourceWellKey: string;

  destinationPlate: string;
  destinationPlateKey: string;
  destinationWell: string;
  destinationWellKey: string;

  liquidId: string;
  sampleId: string;
  transferType: string;
  pipetteId: string;


  volume: string;
  transferTime: string;
  transferDescription: string;

}

export type TransferParams = {
  plateId?: string;
  pageSize?: string;
  currentPage?: string;
}

