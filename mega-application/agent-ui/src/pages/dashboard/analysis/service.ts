import request from '@/utils/request';
import type { AnalysisData } from './data';

export async function fakeChartData(): Promise<{ data: AnalysisData }> {
  // return request('/api/fake_analysis_chart_data');
  return request("");
}

/**
 * 获取项目介绍、状态、链接等情况
 */
export async function getDashboard() {
  return request(`/dashboard`, {
    method: 'GET',
  });
}
