const locations = [
  {
    id: 1,
    name: 'Unidade Paulista',
    city: 'São Paulo',
    state: 'SP',
    latitude: -23.5617,
    longitude: -46.6559,
  },
  {
    id: 2,
    name: 'Polo Santos',
    city: 'Santos',
    state: 'SP',
    latitude: -23.9608,
    longitude: -46.3336,
  },
  {
    id: 3,
    name: 'Centro Rio',
    city: 'Rio de Janeiro',
    state: 'RJ',
    latitude: -22.9068,
    longitude: -43.1729,
  },
];

const reports = [
  {
    id: 101,
    locationId: 1,
    locationName: 'Unidade Paulista',
    temperature: 31,
    windSpeed: 18,
    rainProbability: 72,
    weatherCondition: 'Chuva intensa',
    riskLevel: 'ALTO',
    recommendation: 'Reduza deslocamentos e monitore áreas com risco de alagamento.',
    createdAt: new Date(Date.now() - 1000 * 60 * 25).toISOString(),
  },
  {
    id: 102,
    locationId: 2,
    locationName: 'Polo Santos',
    temperature: 28,
    windSpeed: 24,
    rainProbability: 58,
    weatherCondition: 'Ventos fortes',
    riskLevel: 'MODERADO',
    recommendation: 'Acompanhe alertas locais e evite permanência em áreas abertas.',
    createdAt: new Date(Date.now() - 1000 * 60 * 90).toISOString(),
  },
  {
    id: 103,
    locationId: 3,
    locationName: 'Centro Rio',
    temperature: 35,
    windSpeed: 12,
    rainProbability: 18,
    weatherCondition: 'Calor extremo',
    riskLevel: 'BAIXO',
    recommendation: 'Mantenha hidratação e revise a previsão ao longo do dia.',
    createdAt: new Date(Date.now() - 1000 * 60 * 180).toISOString(),
  },
];

function createAxiosResponse(config: any, data: any, status = 200) {
  return {
    data,
    status,
    statusText: status === 201 ? 'Created' : 'OK',
    headers: { 'x-mock-data': 'true' },
    config,
    request: {},
  };
}

function getRequestBody(data: any) {
  if (!data) {
    return {};
  }

  return typeof data === 'string' ? JSON.parse(data) : data;
}

function createReport(location: any) {
  return {
    id: reports.length + 101,
    locationId: location.id,
    locationName: location.name,
    temperature: 29,
    windSpeed: 16,
    rainProbability: 45,
    weatherCondition: 'Dados simulados',
    riskLevel: 'MODERADO',
    recommendation: 'Use estes dados apenas para demonstrar o app sem backend ativo.',
    createdAt: new Date().toISOString(),
  };
}

export async function getMockResponse(config: any) {
  const method = (config.method || 'get').toLowerCase();
  const path = (config.url || '').replace(/^https?:\/\/[^/]+/i, '').replace(/\/$/, '');

  if (method === 'post' && path === '/auth/login') {
    const body = getRequestBody(config.data);

    return createAxiosResponse(config, {
      token: 'mock-token',
      user: {
        id: 1,
        name: 'Usuário Mock',
        email: body.email || 'mock@orbitguard.local',
      },
    });
  }

  if (method === 'post' && path === '/auth/register') {
    const body = getRequestBody(config.data);

    return createAxiosResponse(
      config,
      {
        token: 'mock-token',
        user: {
          id: 1,
          name: body.name || 'Usuário Mock',
          email: body.email || 'mock@orbitguard.local',
        },
      },
      201,
    );
  }

  if (method === 'get' && path === '/locations') {
    return createAxiosResponse(config, locations);
  }

  if (method === 'post' && path === '/locations') {
    const body = getRequestBody(config.data);
    const newLocation = {
      id: locations.length + 1,
      name: body.name || 'Local mock',
      city: body.city || 'Cidade mock',
      state: body.state || 'SP',
      latitude: Number(body.latitude) || -23.5505,
      longitude: Number(body.longitude) || -46.6333,
    };

    locations.push(newLocation);
    reports.unshift(createReport(newLocation));

    return createAxiosResponse(config, newLocation, 201);
  }

  if (method === 'get' && path === '/risk/history') {
    return createAxiosResponse(config, reports);
  }

  if (method === 'get' && path.startsWith('/risk/')) {
    const locationId = Number(path.split('/').pop());
    const report = reports.find((item) => item.locationId === locationId);

    if (report) {
      return createAxiosResponse(config, report);
    }
  }

  return null;
}
