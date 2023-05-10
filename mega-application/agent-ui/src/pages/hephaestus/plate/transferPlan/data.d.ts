
export interface TransferPlanData  {
  id: string;
  projectId: string;

  name: string;
  description: string,
  sourcePlateType: string;
  destinationPlateType: string;

  stepKey: string;
  sortOrder: string;
  sampleTransferMethod: string;
  transferType: string;

  pipetteId: string;
  pipetteCount: string;
  liquidId: string;
  liquidName: string;


  volume: string;
  wellRange: string;

}

export type TransferPlanParams = {
  plateId?: string;
  pageSize?: string;
  currentPage?: string;
}

